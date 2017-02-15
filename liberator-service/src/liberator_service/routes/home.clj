(ns liberator-service.routes.home
  (:require [compojure.core :refer :all]
            [liberator.core
              :refer [defresource resource request-method-in]]))

(defresource home
  :handle-ok "hello world!"
  :etag "fixed-etag"
  :available-media-types ["text/plain"])

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
  (ANY "/combined" [] combined))
