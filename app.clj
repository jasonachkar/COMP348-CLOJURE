(ns app
  (:require [db])
  (:require [menu]))

(defn -main []
  ;; It initializes 'citiesDB' by loading data from the file "cities.txt" using the 'load-data' function from the 'db' namespace.
  (let [citiesDB (db/load-data "cities.txt")]
    (loop []
      ;; It initializes 'citiesDB' by loading data from the file "cities.txt" using the 'load-data' function from the 'db' namespace.
      (let [user-choice (menu/show-menu citiesDB)]
          ;; If 'user-choice' is 5, the loop terminates, ending the program.
          ;; If 'user-choice' is not 5, the 'recur' function is called to repeat the loop.
        (when (not= user-choice 5)
          (recur))))))

(-main)
