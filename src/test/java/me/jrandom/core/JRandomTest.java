package me.jrandom.core;

import static java.util.function.Predicate.isEqual;
import static me.jrandom.core.builder.util.BuildingStrategyUtils.all;
import static me.jrandom.core.builder.util.BuildingStrategyUtils.sequential;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Index.atIndex;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class JRandomTest {

  private JRandom jRandom = JRandom.builder().build();

  @Test
  public void getOne_notSpecifiedFields_fieldsAreNull() {
    TestPojo expected = new TestPojo();

    TestPojo oneInstance = jRandom.getOne(TestPojo.class).build();
    assertThat(oneInstance).isEqualToComparingFieldByFieldRecursively(expected);
  }

  @Test
  public void getOne_onlyRandomName_ok() {
    TestPojo expected = new TestPojo();

    TestPojo oneInstance = jRandom.getOne(TestPojo.class)
        .random(TestPojo::setName)
        .build();

    assertThat(oneInstance).isEqualToIgnoringGivenFields(expected, "name");
    assertThat(oneInstance.getName())
        .isNotBlank();
  }

  @Test
  public void getList_notSpecifiedFields_fieldsAreNull() {
    TestPojo expected = new TestPojo();

    List<TestPojo> listOfInstances = jRandom.getList(TestPojo.class, 5).build();
    assertThat(listOfInstances)
        .size().isEqualTo(5);
    listOfInstances.forEach(instance -> assertThat(instance).isEqualToComparingFieldByFieldRecursively(expected));
  }

  @Test
  public void getList_onlyRandomName_ok() {
    TestPojo expected = new TestPojo();

    List<TestPojo> listOfInstances = jRandom.getList(TestPojo.class, 5)
        .random(TestPojo::setName)
        .build();

    assertThat(listOfInstances)
        .size().isEqualTo(5);
    listOfInstances.forEach(instance -> assertThat(instance).isEqualToIgnoringGivenFields(expected, "name"));
    listOfInstances.forEach(instance -> assertThat(instance.getName()).isNotBlank());
  }

  @Test
  public void getSet_notSpecifiedFields_fieldsAreNull() {
    TestPojo expected = new TestPojo();

    Set<TestPojo> setOfInstances = jRandom.getSet(TestPojo.class, 5).build();
    assertThat(setOfInstances)
        .size().isEqualTo(5);
    setOfInstances.forEach(instance -> assertThat(instance).isEqualToComparingFieldByFieldRecursively(expected));
  }

  @Test
  public void getSet_onlyRandomName_ok() {
    TestPojo expected = new TestPojo();

    Set<TestPojo> setOfInstances = jRandom.getSet(TestPojo.class, 5)
        .random(TestPojo::setName)
        .build();
    assertThat(setOfInstances)
        .size().isEqualTo(5);
    setOfInstances.forEach(instance -> assertThat(instance).isEqualToIgnoringGivenFields(expected, "name"));
    setOfInstances.forEach(instance -> assertThat(instance.getName()).isNotBlank());
  }

  @Test
  public void getCollection_pojoTypeWithSizeAndCollection_ok() {
    TestPojo expected = new TestPojo();

    Collection<TestPojo> collectionOfInstances = jRandom.getCollection(TestPojo.class, new LinkedList<>(), 5).build();
    assertThat(collectionOfInstances)
        .isInstanceOf(LinkedList.class)
        .size().isEqualTo(5);
    collectionOfInstances.forEach(instance -> assertThat(instance).isEqualToComparingFieldByFieldRecursively(expected));
  }

  @Test
  public void getCollection_withCustomSetterAll_ok() {
    TestPojo expected = new TestPojo();

    Collection<TestPojo> collectionOfInstances = jRandom.getCollection(TestPojo.class, new ArrayList<>(), 5)
        .set(TestPojo::setName, all("custom name"))
        .build();

    assertThat(collectionOfInstances)
        .extracting(TestPojo::getName)
        .allMatch(isEqual("custom name"));
    collectionOfInstances.forEach(instance -> assertThat(instance).isEqualToIgnoringGivenFields(expected, "name"));
  }

  @Test
  public void getCollection_withCustomSetterSequential_ok() {
    TestPojo expected = new TestPojo();

    Collection<TestPojo> collectionOfInstances = jRandom.getCollection(TestPojo.class, new ArrayList<>(), 5)
        .set(TestPojo::setName, sequential("First", "Second"))
        .build();

    assertThat(collectionOfInstances)
        .extracting(TestPojo::getName)
        .contains("First", atIndex(0))
        .contains("Second", atIndex(1))
        .contains(null, atIndex(2))
        .contains(null, atIndex(3))
        .contains(null, atIndex(4));

    collectionOfInstances.forEach(instance -> assertThat(instance).isEqualToIgnoringGivenFields(expected, "name"));

  }
}