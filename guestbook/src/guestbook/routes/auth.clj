(ns guestbook.routes.auth
  (:require [compojure.core :refer [defroutes GET POST]]
            [guestbook.views.layout :as layout]
            [hiccup.form :refer
              [form-to label text-field password-field submit-field]]))

(defn registration-page []
  (layout/common
    (form-to [:post "/register"]
             (label "id" "screen name")
             (text-field "id")
             [:br]
             (label "pass" "password")
             (password-field "pass")
             [:br]
             (label "pass1" "retype password")
             (password-field "pass1")
             [:br]
              (submit-button "create account"))))
