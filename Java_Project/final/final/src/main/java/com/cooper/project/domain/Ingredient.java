package com.cooper.project.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="INGREDIENTS")
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class Ingredient implements Serializable{
	 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="INGREDIENT_ID", unique=true, length=3)
	private Long ingredientId;
	
	@Column(name="INGREDIENT_NAME", nullable=false, length=40)
	private String ingredientName;
	
	@Column(name="INGREDIENT_UNIT", length=40)
	private String ingredientUnit;
	
	@Column(name="QOH", unique=true, length=20)
	private String qoh;
	
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RecipeIngredient> recipeIngredients;
}
