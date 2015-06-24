/* Copyright */
package thredds.server.catalog;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import thredds.TestWithLocalServer;
import thredds.util.ContentType;
import ucar.unidata.test.util.NeedsCdmUnitTest;

import java.util.Arrays;
import java.util.Collection;

/**
 * Sanity check opening Feature Collection catalogs
 *
 * @author caron
 * @since 4/20/2015
 */
@RunWith(Parameterized.class)
@Category(NeedsCdmUnitTest.class)
public class TestTdsFCcatalogs {
  @Parameterized.Parameters
  public static Collection<Object[]> getTestParameters() {
    return Arrays.asList(new Object[][]{
            {"catalogGrib", ""},
            {"grib/NDFD/CONUS_5km/catalog", ""},
            {"grib/NDFD/CONUS_5km/latest", ""},

            {"catalogFmrc", ""},
            {"testGFSfmrc/catalog", ""},
            {"testGFSfmrc/catalog", "?dataset=testGFSfmrc/GFS_CONUS_80km_nc_best.ncd"},
            {"testNAMfmrc/runs/catalog", ""},
            {"testNAMfmrc/runs/catalog", "?dataset=testNAMfmrc/runs/NAM_FMRC_RUN_2006-09-26T00:00:00Z"},
    });
  }
  private static final boolean show = false;

  String path, query;

  public TestTdsFCcatalogs(String path, String query) {
    this.path = path;
    this.query = query;
  }


  @Test
  public void testOpenXml() {
    String endpoint = TestWithLocalServer.withPath("catalog/"+path+".xml"+query);
    String response = TestWithLocalServer.testWithHttpGet(endpoint, ContentType.xml);
    if (show)
      System.out.printf("%s%n", response);
  }

  @Test
  public void testOpenHtml() {
    String endpoint = TestWithLocalServer.withPath("catalog/"+path+".html"+query);
    String response = TestWithLocalServer.testWithHttpGet(endpoint, ContentType.html);
    if (show)
      System.out.printf("%s%n", response);
  }

}
