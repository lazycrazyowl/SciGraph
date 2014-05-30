/**
 * Copyright (C) 2014 Christopher Condit (condit@sdsc.edu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.sdsc.scigraph.internal.reachability;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;

@RunWith(Parameterized.class)
public class ReachabilityIndexPerfIT extends AbstractBenchmark {

  final File graph;
  ReachabilityIndex index;
  GraphDatabaseService graphDb;

  public ReachabilityIndexPerfIT(File graph) {
    checkArgument(graph.exists(), graph + " doesn't exist!");
    this.graph = graph;
  }

  @Parameters
  public static Collection<Object[]> data() {
    Object[][] data = new Object[][] {new File("performanceGraphs").listFiles()};
    return Arrays.asList(data);
  }

  @Before
  public void setup() {
    graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(graph.getAbsolutePath());
    index = new ReachabilityIndex(graphDb);
  }

  @After
  public void teardown() {
    graphDb.shutdown();
  }

  @Test
  public void testGetHopCoverage() {
    index.getHopCoverages();
  }

}
