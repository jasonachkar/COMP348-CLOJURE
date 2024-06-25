(ns menu
  (:require [clojure.string :as str]))
;; The 'menu' namespace is defined here, and it requires the 'clojure.string' namespace, aliasing it as 'str'.

(defn print-menu []
  (println "*** City Information Menu ***")
  (println "-----------------------------")
  (println "1. List Cities")
  (println "2. Display City Information")
  (println "3. List Provinces")
  (println "4. Display Province Information")
  (println "5. Exit")
  (println "Enter an option?"))
;; The 'print-menu' function prints the main menu options to the console.

(defn list-cities [cities]
  (println "1.1 List all cities, ordered by city name (ascending)")
  (let [sorted-cities (sort-by :city cities)
        city-names (map :city sorted-cities)
        formatted-cities (str/join " " (map #(str "\"" % "\"") city-names))]
    (println (str "[" formatted-cities "]"))))
;; The 'list-cities' function prints a list of all cities, ordered by city name in ascending order.
;; It takes a collection of cities, sorts them by the city name, formats them, and prints them as a list.

(defn list-cities-by-province [cities]
  (println "Enter the province name:")
  (flush)
  (let [province (str/lower-case (read-line))
        filtered-cities (filter #(= province (str/lower-case (:province %))) cities)
        sorted-cities (sort-by (juxt :size :city) filtered-cities)]
    (doseq [[index city] (map-indexed vector sorted-cities)]
      (println (str (inc index) ": [\"" (:city city) "\" \"" (:size city) "\" " (:population city) "]")))))
;; The 'list-cities-by-province' function lists all cities for a given province, ordered by size (descending) and name (ascending).
;; It prompts the user to enter a province name, converts it to lowercase, filters and sorts the cities, and prints them in the specified order.

(defn list-cities-by-density [cities]
  (println "Enter the province name:")
  (flush)
  (let [province (str/lower-case (read-line))
        filtered-cities (filter #(= province (str/lower-case (:province %))) cities)
        sorted-cities (sort-by #(/ (:population %) (:area %)) filtered-cities)]
    (doseq [city sorted-cities]
      (let [density (/ (:population city) (:area city))]
        (println (str "[\"" (:city city) "\" \"" (:province city) "\" \"" (:size city) "\" "
                      (:population city) " " (format "%.2f" density) "]"))))))
;; The 'list-cities-by-density' function lists all cities for a given province, ordered by population density in ascending order.
;; It prompts the user to enter a province name, converts it to lowercase, filters and sorts the cities by population density, and prints them.

(defn display-city-info [cities]
  (println "Enter the city name:")
  (flush)
  (let [city-name (str/lower-case (read-line))
        city (first (filter #(= city-name (str/lower-case (:city %))) cities))]
    (if city
      (let [density (/ (:population city) (:area city))]
        (println (str "[\"" (:city city) "\" \"" (:province city) "\" \"" (:size city) "\" "
                      (:population city) " " (format "%.2f" density) "]")))
      (println "City not found."))))
;; The 'display-city-info' function displays information about a specific city.
;; It prompts the user to enter a city name, converts it to lowercase, finds the city in the list, and prints its information including the population density.
;; If the city is not found, it prints "City not found."

(defn list-provinces [cities]
  (let [grouped (group-by :province cities)
        province-counts (map (fn [[k v]] [k (count v)]) grouped)
        sorted-provinces (sort-by second > province-counts)]
    (doseq [[index [province count]] (map-indexed vector sorted-provinces)]
      (println (str (inc index) ": [\"" province "\" " count "]")))
    (println (str "Total: " (count grouped) " provinces, " (count cities) " cities on file."))))
;; The 'list-provinces' function lists all provinces and the number of cities in each province.
;; It groups the cities by province, counts the cities in each province, sorts them by the count in descending order, and prints the list.
;; It also prints the total number of provinces and cities.

(defn display-province-info [cities]
  (let [grouped (group-by :province cities)
        province-population (map (fn [[k v]] [k (reduce + (map :population v))]) grouped)
        sorted-provinces (sort-by (comp - second) province-population)]
    (doseq [[index [province population]] (map-indexed vector sorted-provinces)]
      (println (str (inc index) ": [\"" province "\" " population "]")))))
;; The 'display-province-info' function displays information about each province, including the total population.
;; It groups the cities by province, calculates the total population for each province, sorts them by population in descending order, and prints the list.

(defn show-menu [cities]
  (loop []
    (print-menu)
    (flush)
    (let [choice (read-line)]
      (case choice
        "1" (do (println "1. List Cities")
                (println "  1.1 List all cities, ordered by city name (ascending)")
                (println "  1.2 List all cities for a given province, ordered by size (descending) and name (ascending)")
                (println "  1.3 List all cities for a given province, ordered by population density in ascending order")
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
;; The 'show-menu' function displays the main menu and handles user input.
;; It uses a loop to repeatedly display the menu, read the user's choice, and perform the corresponding action.
;; It supports listing cities, displaying city information, listing provinces, and displaying province information.
;; If the user chooses to exit (option 5), it prints "Good Bye" and exits the program.
;; If an invalid option is selected, it prints an error message and prompts the user to try again.
