import com.greetings.GreetingSupplierImpl;
import com.api.GreetingSupplier;

module com.greetings {
        exports com.greetings;
        requires com.api;
        provides GreetingSupplier with GreetingSupplierImpl;
        }
