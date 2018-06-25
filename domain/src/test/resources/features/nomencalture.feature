Feature: Nomenclature

  Scenario: Get all items with custom parameters
    Given nomenclature name "countryName"
    Given optional custom parameters:
      | selectedFields | sortField | sortDirection | pagingPacket | offset |
      |                |           |               |              |        |
    When get all items
    Then return all items