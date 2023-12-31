package run.itlife.service;

import run.itlife.dto.BugsDto;
import run.itlife.entity.Bugs;

import java.util.List;

public interface BugsService {

    void create(BugsDto bugsDto);
    List<Bugs> listAllBugs();
    void createFromKafka(BugsDto bugsDto);
}
