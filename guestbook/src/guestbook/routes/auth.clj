(ns guestbook.routes.auth
  (:require [compojure.core :refer [defroutes GET POST]]
            [guestbook.views.layout :as layout]
            [noir.response :refer [redirect]]
            [noir.session :as session]
            [noir.validation :refer [rule errors? has-value? on-error]]
            [hiccup.form :refer
              [form-to label text-field password-field submit-button]]))

(defn format-error [[error]]
  [:p.error error])

(defn control [field name text]
  (list (on-error name format-error)
        (label name text)
        (field name)
        [:br]))

(defn registration-page []
  (layout/common
    (form-to [:post "/register"]
      (control text-field :id "screen name")
      (control password-field :pass "password")
      (control password-field :pass1 "retype password")
      (submit-button "create account"))))

(defn login-page [& [error]]
  (layout/common
    (form-to [:post "/login"]
      (control text-field :id "screen name")
      (control password-field :pass "password")
      (submit-button "login"))))

(defn handler-login-dead [id pass]
  (cond
    (empty? id)
    (login-page "screen name is required")
    (empty? pass)
    (login-page "password is required")
    (and (= "foo" id) (= "bar" pass))
    (do
      (session/put! :user id)
      (redirect "/"))
    :else
    (login-page "authentication failed")))

(defn handler-login [id pass]
  (rule (has-value? id)
    [:id "screen name is required"])
  (rule (has-value? pass)
    [:pass "password is required"])
  (rule (= id "foo")
    [:id "unknown user"])
  (rule (= pass "bar")
    [:pass "invalid password"])

  (if (errors? :id :pass)
    (login-page)
    (do
      (session/put! :user id)
      (redirect "/"))))

(defroutes auth-routes
  (GET "/register" [] (registration-page))
  (POST "/register" [id pass pass1]
        (if (= pass pass1)
          (redirect "/")
          (registration-page)))
  (GET "/login" [] (login-page))
  (POST "/login" [id pass]
        (handler-login id pass))
  (GET "/logout" []
        (layout/common
          (form-to [:post "/logout"]
            (submit-button "logout"))))
  (POST "/logout" []
        (session/clear!)
        (redirect "/")))
