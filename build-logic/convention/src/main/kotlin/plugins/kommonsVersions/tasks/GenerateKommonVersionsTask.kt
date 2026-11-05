/*
 * GenerateKommonVersionsTask.kt
 * Copyright (c) 2026. All rights reserved
 */
package plugins.kommonsVersions.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import plugins.kommonsVersions.extensions.writeGeneratedFile

/**
 * Tarea encargada de la escritura física de los archivos .kt generados.
 *
 * Utiliza propiedades de tipo [Property] y [DirectoryProperty] para soportar
 * configuración perezosa y ser compatible con el Configuration Cache de Gradle.
 */
@CacheableTask
abstract class GenerateKommonVersionsTask : DefaultTask() {

    @get:OutputDirectory @get:Optional
    abstract val commonMainDir: DirectoryProperty

    @get:OutputDirectory @get:Optional
    abstract val iosMainDir: DirectoryProperty

    @get:OutputDirectory @get:Optional
    abstract val androidMainDir: DirectoryProperty

    @get:Input @get:Optional
    abstract val commonContent: Property<String>

    @get:Input @get:Optional
    abstract val iosContent: Property<String>

    @get:Input @get:Optional
    abstract val androidContent: Property<String>

    @TaskAction
    fun generate() {
        val fileName = "KommonVersions.kt"

        // Encabezado informativo en la consola
        logger.lifecycle("--------------------------------------------------")
        logger.lifecycle("🚀 Generando archivos de Kommon Versions")

        if (androidContent.isPresent && androidMainDir.isPresent) {
            val dir = androidMainDir.get().asFile
            writeGeneratedFile(dir, fileName, androidContent.get())
            logger.lifecycle("✅ [Android] -> ${dir.absolutePath}/$fileName")
        }
        if (commonContent.isPresent && commonMainDir.isPresent) {
            val dir = commonMainDir.get().asFile
            writeGeneratedFile(dir, fileName, commonContent.get())
            logger.lifecycle("✅ [Common]  -> ${dir.absolutePath}/$fileName")
        }
        if (iosContent.isPresent && iosMainDir.isPresent) {
            val dir = iosMainDir.get().asFile
            writeGeneratedFile(dir, fileName, iosContent.get())
            logger.lifecycle("✅ [iOS]     -> ${dir.absolutePath}/$fileName")
        }

        logger.lifecycle("--------------------------------------------------")
    }
}
