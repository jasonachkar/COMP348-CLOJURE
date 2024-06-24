(ns db
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (let [[city province size population area] (str/split line #"\|")]
    {:city city
     :province province
     :size size
     :population (read-string population)
     :area (read-string area)}))

(defn load-data [filename]
  (let [content (slurp filename)
        lines (str/split-lines content)]
    (map parse-line lines)))
