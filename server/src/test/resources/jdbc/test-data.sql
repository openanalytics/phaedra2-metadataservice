-- noinspection SqlResolveForFile
SET SCHEMA 'metadata';

TRUNCATE hca_property RESTART IDENTITY CASCADE;
TRUNCATE hca_tag RESTART IDENTITY CASCADE;
TRUNCATE hca_tagged_object RESTART IDENTITY CASCADE;

insert into hca_property values (1000, 1000, 'PROJECT', 'NumberOfExperiments', '10');
insert into hca_property values (1100, 2000, 'PROJECT', 'NumberOfExperiments', '8');
insert into hca_property values (1200, 3000, 'PROJECT', 'NumberOfExperiments', '6');

insert into hca_property values (2000, 1000, 'PROTOCOL', 'NumberOfFeatures', '10');
insert into hca_property values (2100, 2000, 'PROTOCOL', 'NumberOfFeatures', '8');
insert into hca_property values (2200, 3000, 'PROTOCOL', 'NumberOfFeatures', '6');

insert into hca_property values (3000, 1000, 'FEATURE', 'Type', 'Calculation');
insert into hca_property values (3100, 2000, 'FEATURE', 'Type', 'Normalization');
insert into hca_property values (3200, 3000, 'FEATURE', 'Type', 'Annotation');

insert into hca_property values (4000, 1000, 'EXPERIMENT', 'NumberOfPlates', '10');
insert into hca_property values (4100, 2000, 'EXPERIMENT', 'NumberOfPlates', '10');
insert into hca_property values (4200, 3000, 'EXPERIMENT', 'NumberOfPlates', '10');

insert into hca_property values (5000, 1000, 'PLATE', 'Size', '8 x 16');
insert into hca_property values (5100, 2000, 'PLATE', 'Size', '16 x 24');
insert into hca_property values (5200, 3000, 'PLATE', 'Size', '16 x 24');

insert into hca_property values (6000, 1000, 'WELL', 'Format', '#.##');
insert into hca_property values (6100, 2000, 'WELL', 'Format', '#.##');
insert into hca_property values (6100, 3000, 'WELL', 'Format', '#.##');

insert into hca_tag values (1000, 'Tag0');
insert into hca_tag values (1001, 'Tag1');
insert into hca_tag values (1002, 'Tag2');
insert into hca_tag values (1003, 'Tag3');
insert into hca_tag values (1004, 'Tag4');
insert into hca_tag values (1005, 'Tag5');
insert into hca_tag values (1006, 'Tag6');
insert into hca_tag values (1007, 'Tag7');
insert into hca_tag values (1008, 'Tag8');
insert into hca_tag values (1009, 'Tag9');

insert into hca_tagged_object values (1000, 1000, 'PROJECT', 1000);
insert into hca_tagged_object values (1001, 1000, 'PROJECT', 1001);

insert into hca_tagged_object values (1002, 1000, 'PROTOCOL', 1002);
insert into hca_tagged_object values (1003, 2000, 'PROTOCOL', 1003);

insert into hca_tagged_object values (1004, 1000, 'EXPERIMENT', 1004);
insert into hca_tagged_object values (1005, 2000, 'EXPERIMENT', 1005);

insert into hca_tagged_object values (1006, 1000, 'FEATURE', 1006);
insert into hca_tagged_object values (1007, 2000, 'FEATURE', 1007);

insert into hca_tagged_object values (1008, 1000, 'PLATE', 1008);
insert into hca_tagged_object values (1009, 2000, 'PLATE', 1009);


