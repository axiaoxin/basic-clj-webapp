(ns reporting-example.reports
  (:require [clj-pdf.core :refer [pdf template]]))

;; make a pdf
; (pdf
;   [{:header "Wow that was easy"}
;    [:list
;     [:chunk {:style :bold} "A bold item"]
;     "another item"
;     "yet another item"]
;    [:paragraph "I'm a paragraph!"]]
; "doc.pdf")
