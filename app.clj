(ns app
  (:require [db])
  (:require [menu]))

(defn -main []
  (let [citiesDB (db/load-data "cities.txt")]
    (menu/show-menu citiesDB)))

(-main)
