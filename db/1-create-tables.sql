drop table if exists metadata.hca_property;
drop table if exists metadata.hca_tagged_object;
drop table if exists metadata.hca_tag;

create table if not exists metadata.hca_property
(
    id             bigserial,
    object_id      bigint not null,
    object_class   text   not null,
    property_name  text   not null,
    property_value text   not null,
    primary key (object_id, object_class, property_name)
);
grant all on table metadata.hca_property to phaedra_usr;

create table if not exists metadata.hca_tag
(
    id   bigserial,
    name text not null,
    primary key (id)
);
grant all on table metadata.hca_tag to phaedra_usr;

create table if not exists metadata.hca_tagged_object
(
    id           bigserial,
    object_id    bigint not null,
    object_class text   not null,
    tag_id       bigint not null,
    primary key (object_id, object_class, tag_id),
    foreign key (tag_id) references metadata.hca_tag (id) on update cascade
);
grant all on table metadata.hca_tagged_object to phaedra_usr;