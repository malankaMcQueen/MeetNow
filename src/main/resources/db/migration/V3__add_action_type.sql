-- Заполняем категории с фиксированными ID для системы CCNN
INSERT INTO action_type (id, name, weight) VALUES
(1, 'attended', 1.0),
(4, 'save', 0.6),
(3, 'share', 0.4),
(2, 'view', 0.05),
(5, 'ignore', 0.0);