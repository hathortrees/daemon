create table address
(
    id      int          not null
        primary key,
    address varchar(255) null,
    taken   bit          not null,
    constraint UK_h8jov0p3cffixl6yhdy4hb2gi
        unique (address)
);

create table hibernate_sequence
(
    next_val bigint null
);

create table team
(
    id   int          not null
        primary key,
    name varchar(255) null,
    constraint UK_g2l9qqsoeuynt4r5ofdt1x2td
        unique (name)
);

create table mint
(
    id                    varchar(255) not null
        primary key,
    balance               int          null,
    count                 int          not null,
    created               datetime(6)  null,
    dead                  bit          not null,
    send_back_transaction varchar(255) null,
    state                 int          not null,
    transaction           varchar(255) null,
    transaction_date      datetime(6)  null,
    user_address          varchar(255) null,
    deposit_address_id    int          null,
    team_id               int          null,
    mail                  varchar(255) null,
    constraint FKg4llck4yyq3drlsj1r8a7ax9u
        foreign key (team_id) references team (id),
    constraint FKiq5a4t03k6mjwr1ysv3f8r2lc
        foreign key (deposit_address_id) references address (id)
);

create table tree_attributes
(
    id         int          not null
        primary key,
    accessory  varchar(255) null,
    background varchar(255) null,
    eyes       varchar(255) null,
    hair       varchar(255) null,
    mouth      varchar(255) null,
    nose       varchar(255) null,
    tree_type  varchar(255) null
);

create table big_tree
(
    id                 int          not null
        primary key,
    ipfs               varchar(255) null,
    token              varchar(255) null,
    small_tree_id      int          null,
    tree_attributes_id int          null,
    constraint UK_qi3wnl7peewspf016il1hnipt
        unique (token),
    constraint FKjay5ntq6qjpsvuwuvt5bv8m11
        foreign key (tree_attributes_id) references tree_attributes (id)
);

create table small_tree
(
    id                 int          not null
        primary key,
    ipfs               varchar(255) null,
    taken              bit          not null,
    token              varchar(255) null,
    big_tree_id        int          null,
    mint_id            varchar(255) null,
    tree_attributes_id int          null,
    constraint UK_ems16othk83d5bbr12cls9scq
        unique (token),
    constraint FKd82uln9i04xt53xcenbiykhn6
        foreign key (tree_attributes_id) references tree_attributes (id),
    constraint FKdae6q8d5nh7fsslbb0bdj1nd4
        foreign key (mint_id) references mint (id),
    constraint FKg77i1r6qycvrdal906uk4ayod
        foreign key (big_tree_id) references big_tree (id)
);

alter table big_tree
    add constraint FK1tyo1dnsu2dp5rg4rpgwgr431
        foreign key (small_tree_id) references small_tree (id);

