(ns menu
  (:require [clojure.string :as str]))

(defn print-menu []
  (println "*** City Information Menu ***")
  (println "-----------------------------")
  (println "1. List Cities")
  (println "2. Display City Information")
  (println "3. List Provinces")
  (println "4. Display Province Information")
  (println "5. Exit")
  (println "Enter an option?"))

(defn list-cities [cities]
  (println "1.1 List all cities, ordered by city name (ascending)")
  (let [sorted-cities (sort-by :city cities)]
    (println (map :city sorted-cities))))

(defn list-cities-by-province [cities]
  (println "Enter the province name:")
  (flush)
  (let [province (read-line)
        filtered-cities (filter #(= province (:province %)) cities)
        sorted-cities (sort-by (juxt :size :city) filtered-cities)]
    (doseq [city sorted-cities]
      (println (str (:city city) ", " (:size city) ", " (:population city))))))

(defn list-cities-by-density [cities]
  (println "Enter the province name:")
  (flush)
  (let [province (read-line)
        filtered-cities (filter #(= province (:province %)) cities)
        sorted-cities (sort-by #(/ (:population %) (:area %)) filtered-cities)]
    (doseq [city sorted-cities]
      (println (str (:city city) ", " (:size city) ", " (:population city) ", " (/ (:population city) (:area city)))))))

(defn display-city-info [cities]
  (println "Enter the city name:")
  (flush)
  (let [city-name (read-line)
        city (first (filter #(= city-name (:city %)) cities))]
    (if city
      (println city)
      (println "City not found."))))

(defn list-provinces [cities]
  (let [grouped (group-by :province cities)
        province-counts (map (fn [[k v]] [k (count v)]) grouped)
        sorted-provinces (sort-by second > province-counts)]
    (doseq [[province count] sorted-provinces]
      (println (str province ", " count)))
    (println (str "Total: " (count grouped) " provinces, " (count cities) " cities on file."))))

(defn display-province-info [cities]
  (let [grouped (group-by :province cities)
        province-population (map (fn [[k v]] [k (reduce + (map :population v))]) grouped)
        sorted-provinces (sort-by first province-population)]
    (doseq [[province population] sorted-provinces]
      (println (str province ", " population)))))

(defn show-menu [cities]
  (loop []
    (print-menu)
    (flush)
    (let [choice (read-line)]
      (case choice
        "1" (do (println "1. List Cities")
                (println "1.1 List all cities, ordered by city name (ascending)")
                (println "1.2 List all cities for a given province, ordered by size (descending) and name (ascending)")
                (println "1.3 List all cities for a given province, ordered by population density in ascending order")
                (flush)
                (let [sub-choice (read-line)]
                  (case sub-choice
                    "1.1" (list-cities cities)
                    "1.2" (list-cities-by-province cities)
                    "1.3" (list-cities-by-density cities))))
        "2" (display-city-info cities)
        "3" (list-provinces cities)
        "4" (display-province-info cities)
        "5" (do (println "Good Bye")
                (System/exit 0))
        (do (println "Invalid option, please try again.")
            (recur))))))

