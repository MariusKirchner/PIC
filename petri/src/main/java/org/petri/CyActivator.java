package org.petri;

import java.io.File;
import java.util.Properties;

import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.event.CyEventHelper;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.session.CyNetworkNaming;
import org.cytoscape.view.layout.CyLayoutAlgorithmManager;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.work.SynchronousTaskManager;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class CyActivator extends AbstractCyActivator {
	private static Logger logger;
	public CyActivator() {
		super();
	}


	public void start(BundleContext bc) {

		File logFile = new File("petrilog.log");
		System.setProperty("logfile.name", logFile.getAbsolutePath());
		logger = LoggerFactory.getLogger(CyActivator.class);
		logger.info("PetriNet started!");
		CyAppAdapter adapter = getService(bc, CyAppAdapter.class);
		CyNetworkManager cyNetworkManagerServiceRef = getService(bc,CyNetworkManager.class);
		CyNetworkNaming cyNetworkNamingServiceRef = getService(bc,CyNetworkNaming.class);
		CyNetworkFactory cyNetworkFactoryServiceRef = getService(bc,CyNetworkFactory.class);
		CyNetworkViewFactory cyNetworkViewFactoryServiceRef = getService(bc, CyNetworkViewFactory.class);
		CyNetworkViewManager cyNetworkViewManagerServiceRef = getService(bc,CyNetworkViewManager.class);
		CyEventHelper eventHelperServiceRef = getService(bc, CyEventHelper.class);
		CyLayoutAlgorithmManager cyLayoutAlgorithmManagerRef = getService(bc, CyLayoutAlgorithmManager.class);
		SynchronousTaskManager<?> synchronousTaskManagerRef = getService(bc, SynchronousTaskManager.class);
		VisualMappingManager visualMappingManagerRef = getService(bc, VisualMappingManager.class);
		VisualMappingFunctionFactory visualMappingFunctionFactoryRefd = getService(bc, VisualMappingFunctionFactory.class,
				"(mapping.type=discrete)");
		VisualMappingFunctionFactory visualMappingFunctionFactoryRefp = getService(bc, VisualMappingFunctionFactory.class,
				"(mapping.type=passthrough)");
		//Just Petri Things

		PetriPanel petriPanel = new PetriPanel(cyNetworkManagerServiceRef,
				cyNetworkNamingServiceRef,cyNetworkFactoryServiceRef,cyNetworkViewFactoryServiceRef,
				cyNetworkViewManagerServiceRef, eventHelperServiceRef,cyLayoutAlgorithmManagerRef,
				synchronousTaskManagerRef, visualMappingManagerRef,
				visualMappingFunctionFactoryRefd, visualMappingFunctionFactoryRefp, adapter);
		registerService(bc, petriPanel, CytoPanelComponent.class, new Properties());
	}
}
