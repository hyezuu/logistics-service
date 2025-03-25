-- HubDeliverySequence 테이블 데이터 삽입
INSERT INTO p_hub_delivery_sequence (delivery_sequence_id, current_sequence)
VALUES ('92cc5e60-bf58-4123-a030-8f050eeba9da', 1);
-- CompanyDeliverySequence 테이블 데이터 삽입
INSERT INTO p_company_delivery_sequence (delivery_sequence_id, hub_id, current_sequence)
VALUES
    ('7cb1f90b-4acf-4477-9a71-134d80df9803', '50c4d92b-3aa7-4ac8-999c-7815d074d328', 1),
    ('cf1a28ef-01ff-40da-8180-1e2589bdb990', '0ade4814-ac61-400e-bd33-ef6ccae82928', 1);