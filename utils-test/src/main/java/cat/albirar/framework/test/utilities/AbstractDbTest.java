/*
 * This file is part of "albirar-framework".
 * 
 * "albirar-framework" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * "albirar-framework" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with calendar.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2015 Octavi Fornés <ofornes@albirar.cat>
 */

package cat.albirar.framework.test.utilities;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version.Main;

/**
 * Abstract class to configure mongoDB test database.
 * @author <a href="mailto:ofornes@albirar.cat">Octavi Fornés ofornes@albirar.cat</a>
 */
public abstract class AbstractDbTest {

	private static MongodExecutable mongodExecutable;
	private static MongodProcess mongodProcess; 
	
	/**
	 * Init database server.
	 * @throws Exception On error on preparing database
	 */
	@BeforeClass
	public static void startupDb() throws Exception {
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
	 * Shutdown the database server.
	 */
	@AfterClass
	public static void shutdownDb() {
		mongodProcess.stop();
		mongodExecutable.stop();
	}
	/**
	 * Get the name of the collection associated with the indicated entity.
	 * The name is calculated from entity {@link Class#getSimpleName()} with the first letter in lowercase and the rest as is.
	 * @param entity The entity class
	 * @return The name, or null if entity is null.
	 */
	public static final String getCollectionName(Class<?> entity) {
		if(entity != null) {
			return (entity.getSimpleName().substring(0,1).toLowerCase()
					+ entity.getSimpleName().substring(1));
		}
		return null;
	}
}
