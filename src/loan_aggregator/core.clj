(ns loan-aggregator.core
  (:gen-class)
  (:require [clojure.string :as str]))

(defn make-key
  [network product date]
  (try (let [month (second (str/split date #"-"))]
         (list (str/replace network "'" "") (str/replace product "'" "") month))
       (catch NullPointerException e
         (throw (ex-info "Invalid Line, could also be extra new lines at the end of the input file"
                         {:network network
                          :product product
                          :date    date})))))

(defn -main
  [& [arg]]
  (with-open [rdr (if (= arg "--test")
                    (clojure.java.io/reader "Loans.csv")
                    (java.io.BufferedReader. *in*))]
    (let [lines (drop 1 (line-seq rdr))
          out-map (reduce (fn [out-map line]
                            (let [[_msisdn network date product amount] (str/split line #",")
                                  key (make-key network product date)
                                  [acc-amount count] (get out-map key [0 0])
                                  ]
                              (assoc out-map key [(+ acc-amount (bigdec amount)) (inc count)])))
                          {}
                          lines)]
      (with-open [w (clojure.java.io/writer "Output.csv")]
        (.write w (apply str (interpose "," ["(Network Product Month)" "Amount" "Count"])))
        (.newLine w)
        (doseq [[k v] out-map]
          (let []
            (.write w (str k "," v))
            (.newLine w))))))
  (println "Done processing"))
