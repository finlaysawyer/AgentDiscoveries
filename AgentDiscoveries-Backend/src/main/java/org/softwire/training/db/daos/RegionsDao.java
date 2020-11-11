package org.softwire.training.db.daos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softwire.training.models.Region;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class RegionsDao {

    private static final Logger logger = LoggerFactory.getLogger("org.software.training.db");
    private DaoHelper<Region> helper;

    @Inject
    public RegionsDao(EntityManagerFactory entityManagerFactory) {
        this.helper = new DaoHelper<>(entityManagerFactory);
    }

    public Optional<Region> getRegion(int regionId) {
        return helper.getEntity(Region.class, regionId);
    }

    public List<Region> getRegions() {
        return helper.getEntities(Region.class);
    }

    public int createRegion(Region region) {
        helper.createEntity(region);
        logger.info("Region created: " + region.toString());
        return region.getRegionId();
    }

    public void updateRegion(Region region) {
        helper.updateEntity(region);
    }

    public void deleteRegion(int regionId) {
        helper.deleteEntity(Region.class, regionId);
    }
}
