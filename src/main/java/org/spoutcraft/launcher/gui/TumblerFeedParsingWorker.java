/*
 * This file is part of Spoutcraft Launcher (http://www.spout.org/).
 *
 * Spoutcraft Launcher is licensed under the SpoutDev License Version 1.
 *
 * Spoutcraft Launcher is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * Spoutcraft Launcher is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev license version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */
package org.spoutcraft.launcher.gui;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Random;

import javax.swing.JEditorPane;

import org.jdesktop.swingworker.SwingWorker;

public class TumblerFeedParsingWorker extends SwingWorker<Object, Object>{
	JEditorPane editorPane;
	private String username = null;
	private Random rand = new Random();
	public TumblerFeedParsingWorker(JEditorPane editorPane2) {
		this.editorPane = editorPane2;
	}

	public void setUser(String name) {
		username = name;
	}

	@Override
	protected Object doInBackground() throws Exception {
		try {
			URL url = new URL("http://www.acampadas21.net/launcher/novedades.php");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
				editorPane.setVisible(false);
				editorPane.setPage(url);
				try {
					Thread.sleep(5000);
				}
				catch (InterruptedException e) { }

				String text = editorPane.getText();

				text = text.replaceAll("<li>", "- ");
				text = text.replaceAll("</li>", "<br/>");
				text = text.replaceAll("<p>", "");
				text = text.replace("</p>", "<br/>");
				text = text.replaceAll("</p>", "<br/><br/>");
				text = text.replaceAll("@time_of_day", getTimeOfDay());
				text = text.replaceAll("@username", getUsername());
				editorPane.setText(text);
				editorPane.setVisible(true);
			} else {
				editorPane.setText(getErrorMessage());
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return null;
	}

	private String getErrorMessage() {
		String[] errors = {
			"Oh, parece que no se ha podido contactar con el servidor de noticias...",
			"No pude encontrar informaci�n para mostrar aqu&iacute;...",
			"Circulen, no hay nada que ver aqu&iacute;...",
			"Dices que el servidor est&aacute; caid...Eh! Qu&eacute; es eso de ah&iacute;!",
			"Parece que unos %mob%s se han apoderado del servidor!",
			"Oh Noes! Nuestro servidor de noticias ha ca&iacute;do!"

		};
		return errors[rand.nextInt(errors.length)].replaceAll("%mob%", getRandomMob());
	}

	private String getUsername() {
		return username != null ? username : "Player";
	}

	private String getRandomMob() {
		int mob = rand.nextInt(4);
		switch(mob) {
			case 0: return "zombie";
			case 1: return "creeper";
			case 2: return "skeleton";
			case 3: return "ghast";
			default: return "";
		}
	}

	private String getTimeOfDay() {
		int hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		if (hours < 6) {
			return "night";
		}
		if (hours < 12) {
			return "morning";
		}
		if (hours < 14) {
			return "day";
		}
		if (hours < 18) {
			return "afternoon";
		}
		if (hours < 22) {
			return "evening";
		}
		return "night";
	}
}
