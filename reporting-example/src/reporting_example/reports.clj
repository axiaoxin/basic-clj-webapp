(ns reporting-example.reports
  (:require [clj-pdf.core :refer [pdf template]]
            [reporting-example.models.db :as db]))

;; make a pdf
; (pdf
;   [{:header "Wow that was easy"}
;    [:list
;     [:chunk {:style :bold} "A bold item"]
;     "another item"
;     "yet another item"]
;    [:paragraph "I'm a paragraph!"]]
; "doc.pdf")

(def employee-template
  (template [$name $occupation $place $country]))

;; make a pdf by db
; (pdf
;   [{:header "Employee List"}
;     (into [:table
;             {:border false
;              :cell-border false
;              :header [{:color [0 150 150]} "Name" "Occupation" "Place" "Country"]}]
;           (employee-template (db/read-employees)))]
;    "report.pdf")

(def employee-template-paragraph
  (template
    [:paragraph
      [:heading {:style {:size 5}} $name]
      [:chunk {:style :bold} "occupation: "] $occupation "\n"
      [:chunk {:style :bold} "place: "] $place "\n"
      [:chunk {:style :bold} "country: "] $country
      [:spacer]]))

;; make a styled pdf
; (pdf
;   [{}
;    [:heading {:size 10} "Employees"]
;    [:line]
;    [:spacer]
;    (employee-template-paragraph (db/read-employees))]
;  "report-style.pdf")

(defn table-report [out]
  (pdf
    [{:header "Employee List"}
     (into [:table
            {:border false
             :cell-border false
             :header [{:color [0 150 150]} "Name" "Occupation" "Place" "Country"]}]
      (employee-template (db/read-employees)))]
   out))

(defn list-report [out]
  (pdf
    [{}
     [:heading {:size 10} "Employees"]
     [:line]
     [:spacer]
    (employee-template-paragraph (db/read-employees))]
   out))
