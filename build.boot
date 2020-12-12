(defn get-deps [file] (-> file slurp read-string))

(set-env!
  :resource-paths #{"src"}
  :dependencies (get-deps "./dependencies.edn"))

(require
 '[degree9.boot-semver :refer :all])

(task-options!
 pom    {:project 'degree9/uikit-hl
         :description "UIKit Components for Hoplon."
         :url "https://github.com/degree9/uikit-hl"
         :scm {:url "https://github.com/degree9/uikit-hl"}})

(deftask deploy
  "Build project for deployment to clojars."
  []
  (comp
    (version)
    (build-jar)
    (push-release)))

(deftask develop
  "Build project for development."
  []
  (comp
    (version :develop true
             :pre-release 'snapshot)
    (sift :include #{#"uikit_hl/(.*).clj[s]?$"})
    (watch)
    (target :dir #{"target"})
    (build-jar)))
