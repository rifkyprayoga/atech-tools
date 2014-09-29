package com.atech.tools.pdf;

import java.awt.Rectangle;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.icepdf.core.pobjects.Document;
import org.icepdf.ri.common.MyAnnotationCallback;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.common.ViewModel;
import org.icepdf.ri.common.WindowManagementCallback;
import org.icepdf.ri.util.PropertiesManager;

public class WindowManagerIcePdfViewer implements WindowManagementCallback {

	private PropertiesManager properties;
	private ArrayList<SwingController> controllers;
	private long newWindowInvokationCounter = 0L;

	private ResourceBundle messageBundle = null;

	JFrame mainFrame;

	public WindowManagerIcePdfViewer(PropertiesManager properties) {
		this(properties, null);
	}

	public WindowManagerIcePdfViewer(PropertiesManager properties,
			ResourceBundle messageBundle) {
		this.properties = properties;
		this.controllers = new ArrayList<SwingController>();

		if (messageBundle != null) {
			this.messageBundle = messageBundle;
		} else {
			this.messageBundle = ResourceBundle.getBundle("org.icepdf.ri.resources.MessageBundle");
		}

		if (properties.getBoolean("org.icepdf.core.verbose", true)) {
			System.out.println("\nICEsoft ICEpdf Viewer " + Document.getLibraryVersion());
			System.out.println("Copyright ICEsoft Technologies, Inc.\n");
		}
	}

	public PropertiesManager getProperties() {
		return this.properties;
	}

	public long getNumberOfWindows() {
		return this.newWindowInvokationCounter;
	}

	public void newWindow(String location) {
		SwingController controller = commonWindowCreation();
		controller.openDocument(location);
	}

	public void newWindow(URL location) {
		SwingController controller = commonWindowCreation();
		controller.openDocument(location);
	}

	protected SwingController commonWindowCreation() {
		SwingController controller = new SwingController(this.messageBundle);
		controller.setWindowManagementCallback(this);

		controller.setPropertiesManager(this.properties);

		controller.getDocumentViewController()
				.setAnnotationCallback(
						new MyAnnotationCallback(controller
								.getDocumentViewController()));

		this.controllers.add(controller);

		int viewType = 1;
		int pageFit = 4;

		try {
			viewType = getProperties().getInt("document.viewtype", 1);
			pageFit = getProperties().getInt("document.pagefitMode", 4);
		} catch (NumberFormatException e) {
		}

		SwingViewBuilder factory = new SwingViewBuilder(controller, viewType, pageFit);

		mainFrame = factory.buildViewerFrame();
		if (mainFrame != null) {
			int width = getProperties().getInt("application.width", 800);
			int height = getProperties().getInt("application.height", 600);
			mainFrame.setSize(width, height);

			int x = getProperties().getInt("application.x", 1);
			int y = getProperties().getInt("application.y", 1);

			mainFrame.setLocation(
					(int) (x + this.newWindowInvokationCounter * 10L),
					(int) (y + this.newWindowInvokationCounter * 10L));

			this.newWindowInvokationCounter += 1L;
			mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			mainFrame.setVisible(true);
		}

		return controller;
	}

	public void disposeWindow(SwingController controller, JFrame viewer,
			Properties properties) {
		//System.out.println("disposeWindow");

		if (this.controllers.size() <= 1) {
			quit(controller, viewer, properties);
			return;
		}

		int index = this.controllers.indexOf(controller);
		if (index >= 0) {
			this.controllers.remove(index);
			this.newWindowInvokationCounter -= 1L;
			if (viewer != null) {
				viewer.setVisible(false);
				viewer.dispose();
			}
		}
	}

	public void quit(SwingController controller, JFrame viewer,	Properties properties) {
		//System.out.println("quit");

		if ((controller != null) && (viewer != null)) {
			Rectangle sz = viewer.getBounds();
			getProperties().setInt("application.x", sz.x);
			getProperties().setInt("application.y", sz.y);
			getProperties().setInt("application.height", sz.height);
			getProperties().setInt("application.width", sz.width);
			if (properties != null) {
				getProperties().set("document.pagefitMode",
						properties.getProperty("document.pagefitMode"));

				getProperties().set("document.viewtype",
						properties.getProperty("document.viewtype"));
			}
			getProperties().setDefaultFilePath(ViewModel.getDefaultFilePath());
			getProperties().setDefaultURL(ViewModel.getDefaultURL());
		}

		getProperties().saveAndEnd();

		for (int i = 0; i < this.controllers.size(); i++) {
			SwingController c = (SwingController) this.controllers.get(i);
			if (c != null) {
				c.dispose();
			}
		}

//		if (mainFrame != null) {
//			// mainFrame.dispose();
//
//		}

	}

	public void minimiseAllWindows() {
		for (int i = 0; i < this.controllers.size(); i++) {
			SwingController controller = (SwingController) this.controllers
					.get(i);
			JFrame frame = controller.getViewerFrame();
			if (frame != null)
				frame.setState(1);
		}
	}

	public void bringAllWindowsToFront(SwingController frontMost) {
		JFrame frontMostFrame = null;
		for (int i = 0; i < this.controllers.size(); i++) {
			SwingController controller = (SwingController) this.controllers
					.get(i);
			JFrame frame = controller.getViewerFrame();
			if (frame != null)
				if (frontMost == controller) {
					frontMostFrame = frame;
				} else {
					frame.setState(0);
					frame.toFront();
				}
		}

		if (frontMostFrame != null) {
			frontMostFrame.setState(0);
			frontMostFrame.toFront();
		}
	}

	public void bringWindowToFront(int index) {
		if ((index >= 0) && (index < this.controllers.size())) {
			SwingController controller = (SwingController) this.controllers
					.get(index);
			JFrame frame = controller.getViewerFrame();
			if (frame != null) {
				frame.setState(0);
				frame.toFront();
			}
		}
	}

	public List<Object> getWindowDocumentOriginList(SwingController giveIndex) {
		Integer foundIndex = null;
		int count = this.controllers.size();
		List<Object> list = new ArrayList<Object>(count + 1);
		for (int i = 0; i < count; i++) {
			Object toAdd = null;
			SwingController controller = (SwingController) this.controllers.get(i);
			if (giveIndex == controller)
				foundIndex = new Integer(i);
			Document document = controller.getDocument();
			if (document != null)
				toAdd = document.getDocumentOrigin();
			list.add(toAdd);
		}
		if (foundIndex != null)
			list.add(foundIndex);
		return list;
	}

	void updateUI() {
		for (int i = 0; i < this.controllers.size(); i++) {
			SwingController controller = (SwingController) this.controllers.get(i);
			JFrame frame = controller.getViewerFrame();
			if (frame != null) {
				SwingUtilities.updateComponentTreeUI(frame);
			}
		}
	}

}
