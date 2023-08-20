/*
 * Copyright 2017 PingCAP, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pingcap.tispark

import com.pingcap.tikv.catalog.Catalog
import com.pingcap.tikv.meta.{TiDBInfo, TiTableInfo}

import scala.jdk.CollectionConverters._

// Likely this needs to be merge to client project
// and serving inside metastore if any
class MetaManager(var catalog: Catalog) {

  def reloadCatalog(newCatalog: Catalog): Unit = {
    if (this.catalog != newCatalog)
      this.catalog = newCatalog
  }

  def getDatabases: List[TiDBInfo] =
    catalog.listDatabases().asScala.toList

  def getTables(db: TiDBInfo): List[TiTableInfo] =
    catalog.listTables(db).asScala.toList

  def reloadCache(loadTables: Boolean = false): Unit =
    catalog.reloadCache(loadTables)

  def getTable(dbName: String, tableName: String): Option[TiTableInfo] =
    Option(catalog.getTable(dbName, tableName))

  def getDatabase(dbName: String): Option[TiDBInfo] =
    Option(catalog.getDatabase(dbName))

  def getDatabasesFromCache: List[TiDBInfo] =
    catalog.listDatabasesFromCache().asScala.toList

  def getTablesFromCache(db: TiDBInfo): List[TiTableInfo] =
    catalog.listTablesFromCache(db).asScala.toList

  def getTableFromCache(dbName: String, tableName: String): Option[TiTableInfo] =
    Option(catalog.getTable(dbName, tableName))

  def getDatabaseFromCache(dbName: String): Option[TiDBInfo] =
    Option(catalog.getDatabase(dbName))

  def close(): Unit = catalog.close()
}
