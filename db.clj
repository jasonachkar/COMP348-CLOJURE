(ns db
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (let [[city province size population area] (str/split line #"\|")]
    {:city city
     :province province
     :size size
     :population (read-string population)
     :area (read-string area)}))
;; The 'parse-line' function takes a line of text as input and splits it into components using the '|' character as a delimiter.
;; It then constructs and returns a map with keys :city, :province, :size, :population, and :area, converting population and area values to numbers using 'read-string'.
(defn load-data [filename]
  (let [content (slurp filename)
        lines (str/split-lines content)]
    (map parse-line lines)))
;; The 'load-data' function takes a filename as input and reads its content as a string using the 'slurp' function.
;; It splits the content into individual lines and maps each line to a map using the 'parse-line' function.
;; The result is a collection of maps, each representing a city with its associated data.