import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class FeatureModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("mobiletakehome.android.library")
                apply("mobiletakehome.android.hilt")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add("implementation", project(":core:ui"))
                add("implementation", project(":core:common"))
                add("implementation", project(":core:network"))
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:model"))
                add("implementation", project(":core:data"))
                add("implementation", project(":core:domain"))
                add("implementation", project(":core:testing"))

                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
                add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())

                add("implementation", libs.findLibrary("androidx.junit").get())
                add("implementation", libs.findLibrary("androidx.test.core.ktx").get())
                add("implementation", libs.findLibrary("androidx.espresso.core").get())
                add("implementation", libs.findLibrary("androidx.test.rules").get())
                add("implementation", libs.findLibrary("androidx.test.runner").get())
                add("implementation", libs.findLibrary("hilt.android.testing").get())
                add("implementation", libs.findLibrary("kotlinx.coroutines.test").get())

                add("testImplementation", libs.findLibrary("androidx.ui.test.junit4").get())
                add("testImplementation", project(":core:testing"))
                add("testImplementation", libs.findLibrary("kotlinx.coroutines.test").get())
                add("testImplementation", libs.findLibrary("kotlinx.serialization.json").get())

                add("androidTestImplementation", libs.findLibrary("androidx.ui.test.junit4").get())
                add("androidTestImplementation", project(":core:testing"))
                "androidTestImplementation"(
                    libs.findLibrary("androidx.lifecycle.runtimeTesting").get(),
                )
            }
        }
    }
}