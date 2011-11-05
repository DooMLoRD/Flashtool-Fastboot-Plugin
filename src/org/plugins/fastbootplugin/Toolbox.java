package org.plugins.fastbootplugin;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.adb.FastbootUtility;
import org.lang.Language;
import org.logger.MyLogger;
import org.system.OS;

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
					try {
						String bootimg = chooseBootimg();
						MyLogger.debug(bootimg);
						//FastbootUtility.hotBoot(bootimg);
					}
					catch (Exception e1) {
						MyLogger.error(e1.getMessage());
					}
				}
			});
			btnSelectBootimgToHotBoot.setBounds(10, 154, 248, 23);
			contentPanel.add(btnSelectBootimgToHotBoot);
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
	
	public String chooseBootimg() {
		JFileChooser chooser = new JFileChooser(); 
		kernelimgFileFilter newkernelimgFileFilter = new kernelimgFileFilter();
		chooser.setCurrentDirectory(new java.io.File("."));
		
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
