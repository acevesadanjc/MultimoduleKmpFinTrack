/*
 * GenerateFile.kt
 * Copyright (c) 2026. All rights reserved
 */
package plugins.kommonsVersions.extensions

import org.gradle.api.Task
import java.io.File

// Esta se queda igual para ConfigureIosTask (Fase de configuración)
internal fun Task.generateFile(
    outputDir: File,
    fileName: String,
    fileContent: String,
) {
    val outputFile = outputDir.resolve(fileName)

    inputs.property("content_$fileName", fileContent)
    outputs.file(outputFile)

    doLast {
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }

        outputFile.writeText(fileContent)

        logger.lifecycle("--------------------------------------------------")
        logger.lifecycle("✅ [iOS Config Updated] -> ${outputFile.name}")
        logger.lifecycle("📍 Path: ${outputFile.absolutePath}")
    }
}

internal fun writeGeneratedFile(
    outputDir: File,
    fileName: String,
    fileContent: String,
) {
    if (!outputDir.exists()) {
        outputDir.mkdirs()
    }
    val outputFile = outputDir.resolve(fileName)
    outputFile.writeText(fileContent)
}
