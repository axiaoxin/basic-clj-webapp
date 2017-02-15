(ns liberator-service.routes.home
  (:require [compojure.core :refer :all]
            [liberator.core
              :refer [defresource resource request-method-in]]))

(defresource home
  :handle-ok "hello world!"
  :etag "fixed-etag"
  :available-media-types ["text/plain"])

(defroutes home-routes
  (ANY "/" request home))
