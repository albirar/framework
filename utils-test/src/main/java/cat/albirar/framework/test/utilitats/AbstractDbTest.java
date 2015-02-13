/*
 * This file is part of "spike-mongodb".
 * 
 * "spike-mongodb" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "spike-mongodb" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with calendar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2015 Octavi Fornés <ofornes@albirar.cat>
 */

package cat.albirar.framework.test.utilitats;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version.Main;

/**
 * Classe abstracta per a configurar una base de dades.
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 */
public abstract class AbstractDbTest {

	private static MongodExecutable mongodExecutable;
	private static MongodProcess mongodProcess; 
	
	/**
	 * Inicia la base de dades.
	 * @throws Exception En preparar la base de dades
	 */
	@BeforeClass
	public static void iniciarDb() throws Exception {
		MongodConfigBuilder cBuilder;
		Net net;
		MongodStarter runtime;
		
		net = new Net("localhost", 4949, false);
		cBuilder = new MongodConfigBuilder();
		cBuilder.net(net);
		cBuilder.version(Main.PRODUCTION);
		
		runtime = MongodStarter.getDefaultInstance();
		mongodExecutable = runtime.prepare(cBuilder.build());
		mongodProcess = mongodExecutable.start();
	}

	/**
	 * Atura la base de dades.
	 */
	@AfterClass
	public static void aturarDb() {
		mongodProcess.stop();
		mongodExecutable.stop();
	}
	/**
	 * Retorna el nom de la col·lecció associada amb l'entitat indicada.
	 * @param entitat La classe de l'entitat
	 * @return El nom, o null si s'ha indicat una classe nul·la.
	 */
	public static final String getNomColleccio(Class<?> entitat) {
		if(entitat != null) {
			return (entitat.getSimpleName().substring(0,1).toLowerCase()
					+ entitat.getSimpleName().substring(1));
		}
		return null;
	}
}
