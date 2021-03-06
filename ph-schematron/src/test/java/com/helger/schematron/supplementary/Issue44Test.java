/**
 * Copyright (C) 2014-2017 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
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
package com.helger.schematron.supplementary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.oclc.purl.dsdl.svrl.SchematronOutputType;

import com.helger.commons.io.resource.FileSystemResource;
import com.helger.schematron.ISchematronResource;
import com.helger.schematron.SchematronDebug;
import com.helger.schematron.pure.SchematronResourcePure;
import com.helger.schematron.svrl.SVRLHelper;
import com.helger.schematron.svrl.SVRLWriter;
import com.helger.schematron.xslt.SchematronResourceSCH;

public final class Issue44Test
{
  public static void validateAndProduceSVRL (@Nonnull final File aSchematron, final File aXML) throws Exception
  {
    // SchematronResourcePure fails!
    ISchematronResource aSCH = SchematronResourcePure.fromFile (aSchematron);
    // Parsing Schematron works!
    aSCH = SchematronResourceSCH.fromFile (aSchematron);

    // Perform validation
    final SchematronOutputType aSVRL = aSCH.applySchematronValidationToSVRL (new FileSystemResource (aXML));
    assertNotNull (aSVRL);
    if (true)
      System.out.println (SVRLWriter.createXMLString (aSVRL));

    assertEquals (3, SVRLHelper.getAllFailedAssertionsAndSuccessfulReports (aSVRL).size ());
  }

  @Test
  public void testIssue () throws Exception
  {
    SchematronDebug.setSaveIntermediateXSLTFiles (true);

    validateAndProduceSVRL (new File ("src/test/resources/issues/github44/schematron.sch"),
                            new File ("src/test/resources/issues/github44/test.xml"));
  }
}
