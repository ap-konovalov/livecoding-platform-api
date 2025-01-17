package ru.apk.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.apk.exceptions.CompilationException;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CodeController {

    private static final Logger log = LoggerFactory.getLogger(CodeController.class);
    private static final String FILE_NAME = "Main.java";

    @PostMapping("/execute")
    public String executeCode(@RequestBody String code) {
        writeCodeToFile(code, FILE_NAME);
        try {
            compileCode(FILE_NAME);
        } catch (CompilationException e) {
            return "Compilation error: " + e.getMessage();
        }
        return runCodeAndGetResult();
    }

    private static String runCodeAndGetResult() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            Process process = new ProcessBuilder("java", "-cp", System.getProperty("java.io.tmpdir"), FILE_NAME)
                    .redirectErrorStream(true)
                    .start();
            process.getInputStream().transferTo(output);
            process.waitFor();
        } catch (Exception e) {
            log.error(String.format("Error during execution: %s", e.getMessage()));
            Thread.currentThread().interrupt();
        }

        return output.toString();
    }

    private static void compileCode(String fileName) throws CompilationException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnosticsCollector = new DiagnosticCollector<>();
        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnosticsCollector, null, null)) {
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(Arrays.asList(fileName));
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnosticsCollector, null, null, compilationUnits);
            boolean success = task.call();
            if (!success) {
                List<Diagnostic<? extends JavaFileObject>> diagnostics = diagnosticsCollector.getDiagnostics();
                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics) {
                    throw new CompilationException(diagnostic.toString());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeCodeToFile(String code, String fileName) {
        try (var writer = new PrintStream(fileName)) {
            writer.print(code);
        } catch (Exception e) {
            log.error(String.format("Error writing to file: %s", e.getMessage()));
        }
    }
}
