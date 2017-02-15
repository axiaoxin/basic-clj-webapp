(defproject clj-webapp "0.1.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [org.clojure/java.jdbc "0.2.3"]
                 [com.h2database/h2 "1.3.170"]
                 [hiccup "1.0.5"]
                 [ring "1.5.1"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler clj-webapp.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]
                        [hiccup "1.0.5"]
                        [org.clojure/java.jdbc "0.2.3"]
                        [com.h2database/h2 "1.3.170"]
                        [ring "1.5.1"]]}})
