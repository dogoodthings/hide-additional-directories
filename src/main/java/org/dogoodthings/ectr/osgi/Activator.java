package org.dogoodthings.ectr.osgi;

import java.util.Arrays;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.dscsag.plm.spi.interfaces.ECTRService;
import com.dscsag.plm.spi.interfaces.applfilehandling.ApplicationFileHandler;

/**
 * Activator to register provided services
 */
public class Activator implements BundleActivator
{

  @Override
  public void start(BundleContext context) throws Exception
  {
    ECTRService ectrService = getService(context, ECTRService.class);

    Arrays.asList("ACD","ACE","ACM","CAT","EPL","INV","PPT","PRO","RVT","SLE","SLW","UGS","WRD").stream().
        forEach(applicationType -> context.registerService(ApplicationFileHandler.class, new HideAdditionalDirectoriesHandler(applicationType,ectrService), null));

    //... or, with ECTR 5.2.1.0+ you can just use .* as application type instead of registering it for every each application
    // ECTR 5.2.1.0 and higher interprets the application type as regular expression.
    //context.registerService(ApplicationFileHandler.class, new HideAdditionalDirectoriesHandler(".*",ectrService), null);
  }

  @Override
  public void stop(BundleContext context) throws Exception
  {
    // empty
  }

  private <T> T getService(BundleContext context, Class<T> clazz) throws Exception
  {
    ServiceReference<T> serviceRef = context.getServiceReference(clazz);
    if (serviceRef != null)
      return context.getService(serviceRef);
    throw new Exception("Unable to find implementation for service " + clazz.getName());
  }

}