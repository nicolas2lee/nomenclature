Feature: Get item list with sort, paging request use case

  Scenario: Get all items with custom parameters
    Given nomenclature name "france"
    Given default header "*/*"
    Given optional custom parameters:
      | selectedFields                          | sortField | sortDirection | pagingPacket | offset |
      | code, nom_pays_court, code_pays_2, lang | code      | ASC           | 20           | 0      |
    Given nomenclature default config
    When get all items
    Then return all items