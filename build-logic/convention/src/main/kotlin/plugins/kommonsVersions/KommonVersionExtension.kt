/*
 * KommonVersionExtension.kt
 * Copyright (c) 2026. All rights reserved
 */
package plugins.kommonsVersions

import org.gradle.api.Plugin
import org.gradle.api.Project
import plugins.kommonsVersions.templates.commonGeneratedTemplate
import plugins.kommonsVersions.templates.getAndroidGeneratedTemplate
import plugins.kommonsVersions.templates.iosGeneratedTemplate
import plugins.kommonsVersions.extensions.registerIosTask
import plugins.kommonsVersions.tasks.GenerateKommonVersionsTask

const val EXT_NAME = "kommonVersions"
const val GENERATED_PACKAGE = "com/kommon/versions"

/**
 * Plugin de convención para centralizar la gestión de versiones y configuración multiplataforma.
 *
 * Responsabilidades:
 * 1. Definir el DSL [KommonVersionsExtension] para capturar datos de versión.
 * 2. Registrar la tarea de generación de código [GenerateKommonVersionsTask].
 * 3. Automatizar la configuración de Android e iOS mediante inyección inmediata.
 */
class KommonVersionExtension : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Registramos la extensión 'kommonVersions' pasando el 'target' para configuración inmediata
            val ext = extensions.create(
                EXT_NAME,
                KommonVersionsExtension::class.java,
                objects,
                target,
            )

            // Referencias locales para evitar capturar el objeto 'ext' o 'project' en lambdas
            // Esto es crucial para la compatibilidad con el 'Configuration Cache'
            val androidProp = ext.android
            val iosProp = ext.ios
            val generateCodeProp = ext.generateFileVersions

            // 2. Resolvemos el directorio base y el estado del plugin ahora
            val projectDir = layout.projectDirectory
            val packagePath = GENERATED_PACKAGE

            val generateTask = tasks.register("generateKommonVersions", GenerateKommonVersionsTask::class.java) {
                group = "kommon-versions"

                // Opcional: Registra el archivo TOML como una entrada de archivo
                //inputs.file(rootProject.file("gradle/libs.versions.toml"))

                // Solo se ejecuta si el usuario activa el flag en el módulo (evita duplicar clases)
                onlyIf { generateCodeProp.get() }

                // Configuración de contenidos mediante plantillas
                commonContent.set(commonGeneratedTemplate)
                commonMainDir.set(projectDir.dir("src/commonMain/kotlin/$packagePath"))

                // Mapeo perezoso de la configuración de Android
                androidContent.set(androidProp.map { config ->
                    getAndroidGeneratedTemplate(
                        versionCode = config.versionCode,
                        versionName = config.versionName,
                )})
                androidMainDir.set(androidProp.map {
                    val folder = "androidMain"
                    projectDir.dir("src/$folder/kotlin/$packagePath")
                })

                // Configuración perezosa de iOS
                iosContent.set(iosProp.map { iosGeneratedTemplate })
                iosMainDir.set(iosProp.map {
                    projectDir.dir("src/iosMain/kotlin/$packagePath")
                })
            }

            // Enganchamos la generación al ciclo de vida del build para que sea automático
            tasks.matching {  task ->
                task.name.contains("preBuild") || task.name.contains("prepare")
            }.configureEach {
                dependsOn(generateTask)
            }

            // iOS requiere afterEvaluate para interactuar con variables de entorno de Xcode
            afterEvaluate {
                ext.ios.orNull?.let { task -> registerIosTask(iosConfig = task) }
            }
        }
    }
}
