(ns liberator-service.routes.home
  (:require [compojure.core :refer :all]
            [cheshire.core :refer [generate-string]]
            [noir.io :as io]
            [clojure.java.io :refer [file]]
            [liberator.core
              :refer [defresource resource request-method-in]]))
(def users (atom ["john", "Jane"]))

(defresource get-users
  :allowed-methods [:get]
  :handle-ok (fn [_] (generate-string @users))
  :available-media-types ["application/json"])

(defresource add-user
  :method-allowed? (request-method-in :post)
  :malformed? (fn [context]
                (let [params (get-in context [:request :form-params])]
                  (empty? (get params "user"))))
  :handle-malformed "user name cannot be empty!"
  :post!
  (fn [context]
    (let [params (get-in context [:request :form-params])]
      (swap! users conj (get params "user"))))
  :handle-created (fn [_] (generate-string @users))
  :available-media-types ["application/json"])

(defresource home
  :exists?
  (fn [context]
    [(io/get-resource "/home.html")
      {::file (file (str (io/resource-path) "/home.html"))}])
  :handle-ok
  (fn [{{{resource :resource} :route-params} :request}]
    (clojure.java.io/input-stream (io/get-resource "/home.html")))
  :last-modified
  (fn [{{{resource :resource} :route-params} :request}]
    (.lastModified (file (str (io/resource-path) "/home.html"))))
  :etag "fixed-etag"
  :available-media-types ["text/html"])

(defresource service-available
  :service-available? false
  :handle-ok "hello world!"
  :etag "fixed-etag"
  :available-media-types ["text/plain"])

(defresource method-allowed
  :method-allowed?
  (fn [context]
    (= :post (get-in context [:request :request-method])))
  :handle-ok "method allowed"
  :etag "fixed-etag"
  :available-media-types ["text/plain"])

(defresource allowed-methods
  :allowed-methods [:post]
  :handle-ok "allowed methods"
  :etag "fixed-etag"
  :available-media-types ["text/plain"])

(defresource combined
  :service-available? true
  :method-allowed? (request-method-in :post)
  :handle-method-not-allowed
  (fn [context]
    (str (get-in context [:request :request-method]) " is not allowed!!!"))
  :handle-ok "hello world!"
  :etag "fixed-etag"
  :available-media-types ["text/plain"])

(defroutes home-routes
  (ANY "/" request home)
  (ANY "/service-available" [] service-available)
  (ANY "/method-allowed" [] method-allowed)
  (ANY "/allowed-methods" [] allowed-methods)
  (ANY "/combined" [] combined)
  (ANY "/add-user" request add-user)
  (ANY "/users" request get-users))
