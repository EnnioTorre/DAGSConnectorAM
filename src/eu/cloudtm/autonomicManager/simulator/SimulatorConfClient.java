package eu.cloudtm.autonomicManager.simulator;

import eu.cloudtm.autonomicManager.commons.Param;
import eu.cloudtm.autonomicManager.oracles.InputOracle;

/**
 * @author Sebastiano Peluso
 */
class SimulatorConfClient {

   private String systemModel = "CLOSED";
   private String workloadType = "SYNTHETIC";
   private Integer numberOfTransactions = 70;
   private Integer numberOfThreads = 1;
   private String dataItemsAccessDistribution = "UNIFORM";
   private Integer numberOfTxClasses = 2;
   //private Double[] txClassWriteProbability = {0.0, 0.14};
   private Double[] txClassWriteProbability;
   private Integer[] objectAccessDistributionType = {1, 1};
   //private Long[] txClassLength = {500L,27L};
   private Long[] txClassLength;
   //private Double[] txClassProbability = {0.5,0.5};
   private Double[] txClassProbability;
   private String trasactionaLengthType = "FIXED";
   //private Double txArrivalRate = 0.1;
   private Double txArrivalRate;
   //private Long interTransactionThinkTime = 40000L;
   private Long interTransactionThinkTime;
   private Long backoffTime = 50L;
   private Boolean clientPrintStat = true;
   private Boolean clientPrintExecutionInfo = true;
   private Boolean clientVerbose = false;
   private Boolean tlmVerbose = false;
   private Boolean tlmPrintStat = false;

   SimulatorConfClient(InputOracle inputOracle) {

      txClassWriteProbability = new Double[numberOfTxClasses];
      txClassWriteProbability[0] = 0.0D;
      txClassWriteProbability[1] = toDouble(inputOracle.getParam(Param.AvgNumPutsBySuccessfulLocalTx)) / (toDouble(inputOracle.getParam(Param.AvgGetsPerWrTransaction)) + toDouble(inputOracle.getParam(Param.AvgNumPutsBySuccessfulLocalTx)));

      txClassLength = new Long[numberOfTxClasses];
      txClassLength[0] = toLong(inputOracle.getParam(Param.AvgGetsPerROTransaction));
      txClassLength[1] = toLong(inputOracle.getParam(Param.AvgGetsPerWrTransaction)) + toLong(inputOracle.getParam(Param.AvgNumPutsBySuccessfulLocalTx));


      txClassProbability = new Double[numberOfTxClasses];
      txClassProbability[0] = 1.0D - toDouble(inputOracle.getParam(Param.PercentageWriteTransactions));
      txClassProbability[1] = toDouble(inputOracle.getParam(Param.PercentageWriteTransactions));


      txArrivalRate = toDouble(inputOracle.getParam(Param.AvgTxArrivalRate));


      interTransactionThinkTime = toLong(inputOracle.getParam(Param.AvgNTCBTime));

   }

   private static String arrayToConfString(Object[] array) {

      String returnValue;

      if (array != null && array.length > 0) {
         returnValue = "{";
         for (int i = 0; i < array.length - 1; i++) {
            returnValue += array[i] + ", ";
         }

         returnValue += array[array.length - 1] + "}";


         return returnValue;
      }

      return " ";

   }

   private long toLong(Object o) {
      return ((Number) o).longValue();
   }

   private double toDouble(Object o) {
      return ((Number) o).doubleValue();
   }

   @Override
   public String toString() {

      return "[Client]\n\n" +
              "system_model = " + systemModel + "\n" +
              "workload_type = " + workloadType + "\n" +
              "number_of_transactions = " + numberOfTransactions + "\n" +
              "number_of_threads = " + numberOfThreads + "\n" +
              "data_items_access_distribution = " + dataItemsAccessDistribution + "\n" +
              "number_of_tx_classes = " + numberOfTxClasses + "\n" +
              "tx_class_write_probability = " + arrayToConfString(txClassWriteProbability) + "\n" +
              "object_access_distribution_type = " + arrayToConfString(objectAccessDistributionType) + "\n" +
              "tx_class_length = " + arrayToConfString(txClassLength) + "\n" +
              "tx_class_probability = " + arrayToConfString(txClassProbability) + "\n" +
              "transaction_length_type = " + trasactionaLengthType + "\n" +
              "tx_arrival_rate = " + txArrivalRate + "\n" +
              "inter_transaction_think_time = " + interTransactionThinkTime + "\n" +
              "backoff_time = " + backoffTime + "\n" +
              "client_print_stat = " + clientPrintStat + "\n" +
              "client_print_execution_info = " + clientPrintExecutionInfo + "\n" +
              "client_verbose = " + clientVerbose + "\n" +
              "tlm_verbose = " + tlmVerbose + "\n" +
              "tlm_print_stat = " + tlmVerbose;

   }
}
