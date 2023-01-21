package by.it.samatokhin.jd02_03.service;

import by.it.samatokhin.jd02_03.config.Dispatcher;
import by.it.samatokhin.jd02_03.entity.Cashier;
import by.it.samatokhin.jd02_03.entity.Customer;
import by.it.samatokhin.jd02_03.entity.CustomerQueue;
import by.it.samatokhin.jd02_03.entity.Store;
import by.it.samatokhin.jd02_03.util.RandomGenerator;
import by.it.samatokhin.jd02_03.util.Sleeper;

public class CashierWorker implements Runnable {

    public static final int MIN_TIMEOUT_SERVICE_CUSTOMER = 2000;
    public static final int MAX_TIMEOUT_SERVICE_CUSTOMER = 5000;
    private final Cashier cashier;
    private final Dispatcher dispatcher;
    private final CustomerQueue customerQueue;


    public CashierWorker(Cashier cashier, Store store) {
        this.cashier = cashier;
        dispatcher = store.getDispatcher();
        customerQueue = store.getCustomerQueue();
    }


    @Override
    public void run() {
        System.out.println(cashier + " opened");
        while (!dispatcher.storeIsClosed()) {
            Customer customer = customerQueue.poll();
            if (customer != null) {
                synchronized (customer.getMonitor()) {
                    System.out.println(cashier + " started service for " + customer);
                    int timeout = RandomGenerator.get(
                            MIN_TIMEOUT_SERVICE_CUSTOMER,
                            MAX_TIMEOUT_SERVICE_CUSTOMER
                    );
                    Sleeper.sleep(timeout);
                    System.out.println(cashier + " finished service for " + customer);
                    customer.setWaiting(false);
                    customer.notify();
                }
            } else {
                Thread.onSpinWait();
            }
        }
        System.out.println(cashier + " closed");
    }
}
