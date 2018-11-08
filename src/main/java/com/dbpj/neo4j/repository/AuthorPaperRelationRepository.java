package com.dbpj.neo4j.repository;

import com.dbpj.neo4j.node.Author;
import com.dbpj.neo4j.node.Paper;
import com.dbpj.neo4j.relation.AuthorPaperRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author: Jeremy
 * @Date: 2018/11/7 09:09
 */
public interface AuthorPaperRelationRepository extends GraphRepository<AuthorPaperRelation> {

    @Query("MATCH w=(a:author)-[r:publish]->(p:paper) return w")
    List<AuthorPaperRelation> getAllRelationList();

    @Query("MATCH w=(a:author)-[r:publish]->(p:paper) where ID(a) = {aId} return w")
    List<AuthorPaperRelation> findAllByAuthorId(@Param("aId") Long aId);

    @Query("MATCH w=(a:author)-[r:publish]->(p:paper) where a.aName =~ ('(?i).*'+{aName}+'.*') return w")
    List<AuthorPaperRelation> findAllByAuthorName(@Param("aName") String aName);

    @Query("MATCH w=(a:author)-[r:publish]->(p:paper) where ID(p) = {pId} return w")
    List<AuthorPaperRelation> findAllByPaperId(@Param("pId") Long pId);

    @Query("MATCH w=(a:author)-[r:publish]->(p:paper) where p.pTitle =~ ('(?i).*'+{pTitle}+'.*') return w")
    List<AuthorPaperRelation> findAllByPaperTitle(@Param("pTitle") String pTitle);

    @Query("MATCH (a:author),(p:paper) where ID(a)={startId} and ID(p)={endId} " +
            "create w=(a)-[r:publish{aIndex:{aIndex}}]->(p) return w")
    List<AuthorPaperRelation> addRelation(@Param("startId") Long startId, @Param("endId")  Long endId,
                                          @Param("aIndex")  Integer aIndex);

    @Query("MATCH (a:author)-[r:publish]->(p:paper) WHERE ID(r) = {id}" +
            " SET r.aIndex = {aIndex} RETURN r")
    List<AuthorPaperRelation> editRelation(@Param("id") Long id, @Param("aIndex") Integer aIndex);

    @Query("MATCH (a:author)-[r:publish]->(p:paper) WHERE ID(r) IN {idList} DELETE r")
    void deleteRelationIn(@Param("idList") List<Long> idList);
}