object ApplicationId {
    val id = "com.mi.imaginato_pac"
}

const val revisionPrefix = "rev"
const val conjuctionPrefix = "-"
const val pointPrefix = "."

object Release {
    const val versionCode = 1
    const val versionName = "1.0"
    const val compileSdkVersion = 30
    const val targetSdkVersion = 30
    const val minSdkVersion = 23
}

object Config {
    const val gradle = "com.android.tools.build:gradle:4.1.3"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlinVersion}"
    const val navigationGradle =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Version.navigationVersion}"
    const val hiltAndroidGradle =
        "com.google.dagger:hilt-android-gradle-plugin:${Version.hiltVersion}"
    const val kotlinSerialization =
        "org.jetbrains.kotlin:kotlin-serialization:${Version.kotlinVersion}"
}

object Version {
    // Kotlin based
    const val kotlinVersion = "1.4.30"
    const val kotlinCoreVersion = "1.0.2"

    // json
    const val gsonVersion = "2.8.5"

    // hilt
    const val hiltVersion = "2.33-beta"
    const val hiltViewModelVersion = "1.0.0-alpha03"
    const val hiltAndroidXVersion = "1.0.0-alpha03"

    // image
    const val glideVersion = "4.9.0"

    // Networking
    const val coroutinesVersion = "1.3.9"
    const val serializationVersion = "1.0.1"
    const val retrofitVersion = "2.9.0"
    const val okhttpLoggingVersion = "4.9.0"
    const val retrofitKotlinxVersion = "0.8.0"
    const val chuckOkHttpVersion = "1.1.0"

    // Android jetpack
    const val appcompatVersion = "1.2.0"
    const val constraintLayoutVersion = "2.0.0"
    const val navigationVersion = "2.1.0"
    const val lifecycleVersion = "2.0.0"
    const val materialComponentVersion = "1.2.1"
    const val legacySupportVersion = "1.0.0"

    // test
    const val testRunnerVersion = "1.1.1"
    const val junitVersion = "4.12"

    // findBugAnnotation
    const val findBugsVersion = "3.0.2"

    // timber
    const val timber = "4.7.1"

    // circular imageview
    const val circularImageViewVersion = "3.0.0"


    // multidex
    const val multidexVersion = "2.0.1"

    const val roomVersion = "2.1.0"

    // unit test
    const val mockitoVersion = "2.21.0"
    const val mockitoInlineVersion = "2.19.0"
    const val coreTestingVersion = "1.1.1"
    const val mockitoKotlinVersion = "2.1.0"
    const val daggerMockVersion = "0.8.5"
    const val kotlinTestVersion = "3.3.2"

    const val ktlintVersion = "0.39.0"

}

object Dependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlinVersion}"
}

object Log {
    const val timber = "com.jakewharton.timber:timber:${Version.timber}"
}

object Support {
    const val core = "androidx.core:core-ktx:${Version.kotlinCoreVersion}"
    const val appCompat = "androidx.appcompat:appcompat:${Version.appcompatVersion}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Version.constraintLayoutVersion}"
    const val lifeCycleExtensions =
        "androidx.lifecycle:lifecycle-extensions:${Version.lifecycleVersion}"
    const val materialComponent =
        "com.google.android.material:material:${Version.materialComponentVersion}"
    const val legacySupport = "androidx.legacy:legacy-support-v4:${Version.legacySupportVersion}"
    const val multidex = "androidx.multidex:multidex:${Version.multidexVersion}"
}


object Database {
    //room database
    const val room = "androidx.room:room-runtime:${Version.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Version.roomVersion}"
    const val roomKtx = "androidx.room:room-ktx:${Version.roomVersion}"
}

object Image {
    const val glide = "com.github.bumptech.glide:glide:${Version.glideVersion}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Version.glideVersion}"
}

object Arch {
    const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Version.navigationVersion}"
    const val navigationKtx = "androidx.navigation:navigation-ui-ktx:${Version.navigationVersion}"
}

object Network {
    const val kotlinSerialization =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Version.serializationVersion}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofitVersion}"
    const val kotlinxConverter =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Version.retrofitKotlinxVersion}"
    const val okHttpLogging =
        "com.squareup.okhttp3:logging-interceptor:${Version.okhttpLoggingVersion}"

    const val chuckOkHttpDebug =
        "com.readystatesoftware.chuck:library:${Version.chuckOkHttpVersion}"
    const val chuckOkHttpRelease =
        "com.readystatesoftware.chuck:library-no-op:${Version.chuckOkHttpVersion}"
}

object Json {
    const val gson = "com.google.code.gson:gson:${Version.gsonVersion}"
}

object HiltDagger {
    const val hiltAndroid = "com.google.dagger:hilt-android:${Version.hiltVersion}"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Version.hiltVersion}"
    const val hiltViewModel =
        "androidx.hilt:hilt-lifecycle-viewmodel:${Version.hiltViewModelVersion}"
    const val hiltAndroidx = "androidx.hilt:hilt-compiler:${Version.hiltAndroidXVersion}"
}

object TestLibs {
    const val junit = "junit:junit:${Version.junitVersion}"
    const val testRunner = "androidx.test:runner:${Version.testRunnerVersion}"
}

object Annotation {
    const val findBugs = "com.google.code.findbugs:jsr305:${Version.findBugsVersion}"
}

object CircularImageView {
    const val circularImageView = "de.hdodenhof:circleimageview:${Version.circularImageViewVersion}"
}

object UnitTest {
    const val mockitoCore = "org.mockito:mockito-core:${Version.mockitoVersion}"
    const val mockitoInline = "org.mockito:mockito-inline:${Version.mockitoInlineVersion}"
    const val coreTesting = "android.arch.core:core-testing:${Version.coreTestingVersion}"
    const val mockitoKotlin =
        "com.nhaarman.mockitokotlin2:mockito-kotlin:${Version.mockitoKotlinVersion}"
    const val daggerMock =
        "com.github.fabioCollini.daggermock:daggermock-kotlin:${Version.daggerMockVersion}"
    const val kotlinTest = "io.kotlintest:kotlintest-runner-junit5:${Version.kotlinTestVersion}"
}

object KtLint {
    const val ktLintFormat = "com.pinterest:ktlint:${Version.ktlintVersion}"
}
