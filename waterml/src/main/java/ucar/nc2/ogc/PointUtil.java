package ucar.nc2.ogc;

import ucar.ma2.Array;
import ucar.ma2.StructureData;
import ucar.ma2.StructureMembers;
import ucar.nc2.constants.FeatureType;
import ucar.nc2.ft.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Formatter;

/**
 * Created by cwardgar on 2014/02/21.
 */
public class PointUtil {
    /**
     * Opens the dataset at {@code location} as a FeatureDatasetPoint.
     *
     * @param wantFeatureType open this kind of FeatureDataset; may be null, which means search all factories.
     *                        It should be one of the point types.
     * @param location        the URL or file location of the dataset.
     *                        See {@link ucar.nc2.ft.FeatureDatasetFactoryManager#open}.
     * @return a subclass of FeatureDatasetPoint.
     * @throws java.io.IOException     if an I/O error occurs.
     * @throws NoFactoryFoundException if no {@link ucar.nc2.ft.FeatureDatasetFactory} could be found that can open
     *                                 the dataset at {@code location}.
     */
    public static FeatureDatasetPoint openPointDataset(FeatureType wantFeatureType, String location)
            throws IOException, NoFactoryFoundException {
        Formatter errlog = new Formatter();
        FeatureDataset fDset = FeatureDatasetFactoryManager.open(wantFeatureType, location, null, errlog);

        if (fDset == null) {
            throw new NoFactoryFoundException(errlog.toString());
        } else {
            return (FeatureDatasetPoint) fDset;
        }
    }

    public static void printPointFeatures(FeatureDatasetPoint fdPoint, PrintStream outStream) throws IOException {
        for (DsgFeatureCollection featCol : fdPoint.getPointFeatureCollectionList()) {
            StationTimeSeriesFeatureCollection stationCol = (StationTimeSeriesFeatureCollection) featCol;

            for (StationTimeSeriesFeature stationFeat : stationCol) {
                for (PointFeature pointFeature : stationFeat) {
                    StructureData data = pointFeature.getDataAll();

                    for (StructureMembers.Member member : data.getMembers()) {
                        Array memberData = data.getArray(member);
                        outStream.printf("%s: %s    ", member.getName(), memberData);
                    }

                    outStream.println();
                }
            }
        }
    }
}
