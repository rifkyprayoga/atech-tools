package com.atech.data.install_migration;

import lombok.Builder;
import lombok.Data;

/**
 * Created by andy on 07.04.2024.
 */
/**
 *  This file is part of ATech Tools library.
 *
 *  Copyright (C) 2024  Andy (Aleksander) Rozman (Atech-Software)
 *
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *
 *  For additional information about this project please visit our project site on
 *  https://github.com/andyrozman/atech-tools or contact us via this email:
 *  andy@atech-software.com
 *
 *  @author Andy
 *
 */
@Data
@Builder
public class RecipeEntryDto {

    private MigrationOperation migrationOperation;
    private InstallOperation installOperation;
    private String parameter1;
    private String parameter2;
    private String parameter3;

    public void RecipeEntryDto(MigrationOperation migrationOperation, String parameter1) {
        this.migrationOperation = migrationOperation;
        this.parameter1 = parameter1;
    }

    public void RecipeEntryDto(MigrationOperation migrationOperation, String parameter1, String parameter2) {
        this.migrationOperation = migrationOperation;
        this.parameter1 = parameter1;
        this.parameter2 = parameter2;
    }

    public void RecipeEntryDto(InstallOperation installOperation, String parameter1) {
        this.installOperation = installOperation;
        this.parameter1 = parameter1;
    }

    public void RecipeEntryDto(InstallOperation installOperation, String parameter1, String parameter2) {
        this.installOperation = installOperation;
        this.parameter1 = parameter1;
        this.parameter2 = parameter2;
    }

}