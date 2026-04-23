plugins {
    // 規格書 (toml) から Kotlin Multiplatform プラグインを読み込む
    // ここ（中央政府）では適用せず、各州に配る準備だけを行う
    alias(libs.plugins.kotlin.multiplatform) apply false
}

// コマンド実行時に -Prelease=true をつけた時だけ本番モード
val isRelease = project.hasProperty("release") && project.property("release") == "true"
val v = rootProject.findProperty("library.version")?.toString() ?: "unspecified"

allprojects {
    group = "org.bluebikebase"
    version = if (isRelease) v else "$v-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

// 各州（子プロジェクト）に、共通の「外交（Publishing）ルール」を授ける
subprojects {
    // 1. 各州が「外交官(maven-publish)」を雇っている場合のみ、中央政府が介入する
    plugins.withId("maven-publish") {
        configure<PublishingExtension> {
            publications {
                // ここに各州ごとの成果物定義を書く
                // (現状の core や math にある publishing ブロックをここに統合できます)

                // 2. 「maven」という名前の成果物を自動生成
                withType<MavenPublication>().configureEach {
                    if (artifactId == project.name + "-kotlinMultiplatform") artifactId = project.name
                }
            }

            // 3. 輸出先（Nexus）の判定と設定
            repositories {
                val repoType = if (isRelease) "releases" else "snapshots"
                val domain = rootProject.findProperty("repository.domain")?.toString()
                val uri = uri("https://$domain/repository/bluebikebase-$repoType/")

                /*
                maven {
                    url = uri

                    credentials(HttpHeaderCredentials::class) {
                        name = rootProject.findProperty("header.name.cf.access.client.id")?.toString()
                        value = System.getenv("CF_CLIENT_ID")
                    }
                    credentials(HttpHeaderCredentials::class) {
                        name = rootProject.findProperty("header.name.cf.access.client.secret")?.toString()
                        value = System.getenv("CF_CLIENT_SECRET")
                    }

                    authentication {
                        create<HttpHeaderAuthentication>("header")
                    }
                }
                */

                maven {
                    url = uri

                    credentials(PasswordCredentials::class) {
                        username = System.getenv("B3_REPO_USER")
                        password = System.getenv("B3_REPO_PASS")
                    }

                    authentication {
                        create<BasicAuthentication>("basic")
                    }
                }
            }
        }
    }
}
