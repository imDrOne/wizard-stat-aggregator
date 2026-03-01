-- liquibase formatted sql

-- changeset Andrey Tikholoz:20260225132548
-- comment: create_card_stats

create table card_limited_stats
(
    id                         bigserial primary key,
    name                       varchar(255)     not null,
    mtga_id                    integer          not null,
    match_type                 varchar(50)      not null,
    color                      varchar(50)      not null,
    rarity                     varchar(50)      not null,
    url                        text             not null,
    url_back                   text             not null,
    types                      text[]           not null,
    layout                     varchar(100)     not null,
    seen_count                 integer          not null,
    avg_seen                   double precision,
    pick_count                 integer          not null,
    avg_pick                   double precision,
    game_count                 integer          not null,
    pool_count                 integer          not null,
    play_rate                  double precision not null,
    win_rate                   double precision not null,
    opening_hand_game_count    integer          not null,
    opening_hand_win_rate      double precision not null,
    drawn_game_count           integer          not null,
    drawn_win_rate             double precision not null,
    ever_drawn_game_count      integer          not null,
    ever_drawn_win_rate        double precision not null,
    never_drawn_game_count     integer          not null,
    never_drawn_win_rate       double precision not null,
    drawn_improvement_win_rate double precision not null,
    set_code                   VARCHAR(10)      NOT NULL,
    constraint uq_card_stats_mtga_id_match_type unique (mtga_id, set_code, match_type)
);
