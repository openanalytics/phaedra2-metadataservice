-- noinspection SqlResolveForFile

TRUNCATE metadata.hca_property RESTART IDENTITY CASCADE;
TRUNCATE metadata.hca_tag RESTART IDENTITY CASCADE;
TRUNCATE metadata.hca_tagged_object RESTART IDENTITY CASCADE;

insert into metadata.hca_property values (1000, 1000, 'PROJECT', 'NumberOfExperiments', '10');
insert into metadata.hca_property values (1100, 2000, 'PROJECT', 'NumberOfExperiments', '8');
insert into metadata.hca_property values (1200, 3000, 'PROJECT', 'NumberOfExperiments', '6');

insert into metadata.hca_property values (2000, 1000, 'PROTOCOL', 'NumberOfFeatures', '10');
insert into metadata.hca_property values (2100, 2000, 'PROTOCOL', 'NumberOfFeatures', '8');
insert into metadata.hca_property values (2200, 3000, 'PROTOCOL', 'NumberOfFeatures', '6');

insert into metadata.hca_property values (3000, 1000, 'FEATURE', 'Type', 'Calculation');
insert into metadata.hca_property values (3100, 2000, 'FEATURE', 'Type', 'Normalization');
insert into metadata.hca_property values (3200, 3000, 'FEATURE', 'Type', 'Annotation');

insert into metadata.hca_property values (4000, 1000, 'EXPERIMENT', 'NumberOfPlates', '10');
insert into metadata.hca_property values (4100, 2000, 'EXPERIMENT', 'NumberOfPlates', '10');
insert into metadata.hca_property values (4200, 3000, 'EXPERIMENT', 'NumberOfPlates', '10');

insert into metadata.hca_property values (5000, 1000, 'PLATE', 'Size', '8 x 16');
insert into metadata.hca_property values (5100, 2000, 'PLATE', 'Size', '16 x 24');
insert into metadata.hca_property values (5200, 3000, 'PLATE', 'Size', '16 x 24');

insert into metadata.hca_property values (6000, 1000, 'WELL', 'Format', '#.##');
insert into metadata.hca_property values (6100, 2000, 'WELL', 'Format', '#.##');
insert into metadata.hca_property values (6100, 3000, 'WELL', 'Format', '#.##');
