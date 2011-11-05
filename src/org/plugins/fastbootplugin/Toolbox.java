package org.plugins.fastbootplugin;

import gui.FlasherGUI;
import gui.WaitDeviceFastbootGUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.filechooser.FileFilter;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.adb.FastbootUtility;
import org.lang.Language;
import org.logger.MyLogger;

public class Toolbox extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	public static final String version = "1.0";
	public static final String msg1 = "Fastboot Plugin Test1";
	public static final String msg2 = "by DooMLoRD";


	/**
	 * Create the dialog.
	 */
	public Toolbox(){
		setTitle("Fastboot Toolbox");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setModal(true);
		setResizable(false);
		setAlwaysOnTop(true);
		setBounds(100, 100, 429, 289);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblName = new JLabel("Fastboot Toolbox");
			lblName.setHorizontalAlignment(SwingConstants.CENTER);
			lblName.setBounds(10, 11, 403, 14);
			contentPanel.add(lblName);
		}
		{
			JLabel lblVersion = new JLabel("Version "+version);
			lblVersion.setHorizontalAlignment(SwingConstants.CENTER);
			lblVersion.setBounds(10, 36, 403, 14);
			contentPanel.add(lblVersion);
		}
		{
			JLabel lblFrom = new JLabel(msg2);
			lblFrom.setHorizontalAlignment(SwingConstants.CENTER);
			lblFrom.setBounds(10, 61, 403, 14);
			contentPanel.add(lblFrom);
		}
		
		{
			JButton btnRebootIntoFastbootADB = new JButton("Reboot into fastboot mode (via ADB)");
			btnRebootIntoFastbootADB.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						FastbootUtility.adbRebootFastboot();
					}
					catch (Exception e1) {
						MyLogger.error(e1.getMessage());
					}
				}
			});
			btnRebootIntoFastbootADB.setBounds(10, 86, 248, 23);
			contentPanel.add(btnRebootIntoFastbootADB);
		}
		
		{
			JButton btnFastbootReboot = new JButton("Reboot device");
			btnFastbootReboot.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						FastbootUtility.rebootDevice();
					}
					catch (Exception e1) {
						MyLogger.error(e1.getMessage());
					}
				}
			});
			btnFastbootReboot.setBounds(285, 86, 128, 23);
			contentPanel.add(btnFastbootReboot);
		}
		
		{
			JButton btnRebootIntoFastbootFB = new JButton("Reboot into fastboot mode (via Fastboot)");
			btnRebootIntoFastbootFB.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						FastbootUtility.rebootFastboot();
					}
					catch (Exception e1) {
						MyLogger.error(e1.getMessage());
					}
				}
			});
			btnRebootIntoFastbootFB.setBounds(10, 120, 248, 23);
			contentPanel.add(btnRebootIntoFastbootFB);
		}
		
		{
			JButton btnGetDeviceInfo = new JButton("Get Device Info");
			btnGetDeviceInfo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						FastbootUtility.getDeviceInfo();
					}
					catch (Exception e1) {
						MyLogger.error(e1.getMessage());
					}
				}
			});
			btnGetDeviceInfo.setBounds(285, 120, 128, 23);
			contentPanel.add(btnGetDeviceInfo);
		}
		
		{
			JButton btnGetVerInfo = new JButton("Get Ver Info");
			btnGetVerInfo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						FastbootUtility.getFastbootVerInfo();
					}
					catch (Exception e1) {
						MyLogger.error(e1.getMessage());
					}
				}
			});
			btnGetVerInfo.setBounds(285, 154, 128, 23);
			contentPanel.add(btnGetVerInfo);
		}
		{
			JButton btnSelectBootimgToHotBoot = new JButton("Select boot.img to HotBoot");
			btnSelectBootimgToHotBoot.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					hotbootKernel();
				}
			});
			btnSelectBootimgToHotBoot.setBounds(43, 197, 163, 23);
			contentPanel.add(btnSelectBootimgToHotBoot);
		}
		{
			JButton btnSelectBootimgToFlash = new JButton("Select boot.img to Flash");
			btnSelectBootimgToFlash.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					flashKernel();
				}
			});
			btnSelectBootimgToFlash.setBounds(216, 197, 163, 23);
			contentPanel.add(btnSelectBootimgToFlash);
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLanguage();
	}
	
	public void setLanguage() {
		Language.translate(this);
	}

	public void hotbootKernel() {
		try {
			String bootimg = chooseBootimg();
			
			if(bootimg.equals("ERROR")) {
				MyLogger.error("no kernel (boot.img) selected!");
			} 
			else {
				
				MyLogger.info("Selected kernel (boot.img): " + bootimg);

				// just to make sure that device is in fastboot mode
				MyLogger.debug("rebooting device into fastboot mode");
				FastbootUtility.adbRebootFastboot();
				// this wont wait for reply and will move on to next command

				MyLogger.info("HotBooting selected kernel");
				FastbootUtility.hotBoot(bootimg);
			}
		}
		catch (Exception e1) {
			MyLogger.error(e1.getMessage());
		}

	}
	
	public void flashKernel() {
		try {
			String bootimg = chooseBootimg();
			
			if(bootimg.equals("ERROR")) {
				MyLogger.error("no kernel (boot.img) selected!");
			} 
			else {
				
				MyLogger.info("Selected kernel (boot.img): " + bootimg);
				
				// just to make sure that device is in fastboot mode
				MyLogger.debug("rebooting device into fastboot mode");
				FastbootUtility.adbRebootFastboot();
				// this wont wait for reply and will move on to next command
				
				MyLogger.info("Flashing selected kernel");
				FastbootUtility.flashBoot(bootimg);
				
				MyLogger.info("Rebooting device");
				FastbootUtility.rebootDevice();
			}
		}
		catch (Exception e1) {
			MyLogger.error(e1.getMessage());
		}
	}
	
	public String chooseBootimg() {
		JFileChooser chooser = new JFileChooser(new java.io.File(".")); 

		FileFilter ff = new FileFilter(){
			public boolean accept(File f){
				if(f.isDirectory()) return true;
				else if(f.getName().endsWith(".img")) return true;
					else return false;
			}
			public String getDescription(){
				return "IMG files";
			}
		};
		 
		chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
		chooser.setFileFilter(ff);
		
	    chooser.setDialogTitle("Choose kernel file (boot.img)");
	    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    //chooser.setFileFilter(newkernelimgFileFilter);
	    //
	    // disable the "All files" option.
	    //
	    chooser.setAcceptAllFileFilterUsed(false);
	    //    
	    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
	    	return chooser.getSelectedFile().getAbsolutePath();
	    }
	    return "ERROR";
	}
}
