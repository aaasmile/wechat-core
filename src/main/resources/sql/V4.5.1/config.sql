ALTER TABLE `config`
ADD UNIQUE INDEX `cfg_group_cfg_key` (`cfg_group`, `cfg_key`) USING BTREE ;